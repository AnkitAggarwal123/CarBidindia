package com.carbid.demo.service;

import com.carbid.demo.dto.CarDto;
import com.carbid.demo.model.Car;
import com.carbid.demo.model.CarImage;
import com.carbid.demo.repo.ICar;
import com.carbid.demo.repo.ICarImage;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;

@Service
public class CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);


    @Autowired
    ICar carRepo;

    @Autowired
    ICarImage imageRepo;

    @Autowired
    private S3Service s3Service;

    public Car addCar(CarDto carDto) {
        // Check if a car with the same registration number already exists
        Optional<Car> existingCar = carRepo.findByRegistrationNumber(carDto.getRegistrationNumber());
        if (existingCar.isPresent()) {
            throw new IllegalArgumentException("Car with registration number " + carDto.getRegistrationNumber() + " already exists");
        }

        Car car = new Car();
        car.setCarName(carDto.getCarName());
        car.setFuelType(carDto.getFuelType());
        car.setLocation(carDto.getLocation());
        car.setAuctionEndTime(carDto.getAuctionEndTime());
        car.setVisible(carDto.isVisible());
        car.setModelNumber(carDto.getModelNumber());
        car.setOwnerNumber(carDto.getOwnerNumber());
        car.setTransmissionType(carDto.getTransmissionType());
        car.setVehicleDetail(carDto.getVehicleDetail());
        car.setRegistrationNumber(carDto.getRegistrationNumber());
        return carRepo.save(car);
    }

    public String uploadImage(List<MultipartFile> files, Long id){

        for (MultipartFile file : files){
            s3Service.ImageUploader(file, id);
        }
        return "Images uploaded successfully";
    }




    public List<CarDto> allCar() {

        List<Car> cars = carRepo.findAll();

        List<CarDto> carDtos = cars.stream()
                .map(car -> {
                    CarDto carDto = new CarDto();
                    carDto.setCarName(car.getCarName());
                    carDto.setVisible(car.isVisible());
                    carDto.setLocation(car.getLocation());
                    carDto.setFuelType(car.getFuelType());
                    carDto.setModelNumber(car.getModelNumber());
                    carDto.setOwnerNumber(car.getOwnerNumber());
                    carDto.setRegistrationNumber(car.getRegistrationNumber());
                    carDto.setAuctionEndTime(car.getAuctionEndTime());
                    carDto.setVehicleDetail(car.getVehicleDetail());
                    carDto.setTransmissionType(car.getTransmissionType());
                    carDto.setId(car.getId());

                    List<String> imageUrls = car.getCarImages().stream()
                            .map(carImage -> s3Service.getUnSignedUrl(carImage.getSavedName()))
                            .collect(Collectors.toList());
                    carDto.setImageUrls(imageUrls);
                    return carDto;
                }).collect(Collectors.toList());

        return carDtos;

    }

    public String updateVisibility(boolean value, Long id) {
        Car car = carRepo.findById(id).orElseThrow();
        car.setVisible(value);
        carRepo.save(car);
        return "update successfully";
    }

    public String updateAuctionTime(Long carId, LocalDateTime localDateTime) {

        Car car = carRepo.findById(carId).orElseThrow();
        car.setAuctionEndTime(localDateTime);
        carRepo.save(car);
        return "car AuctionEnd Time updated";
    }

    public ResponseEntity<InputStreamResource> downloadImages(Long id) {
        try {
            // Fetch the car by ID, or throw an exception with a custom message if not found
            Car car = carRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Car with ID " + id + " not found."));

            List<String> imageUrls = car.getCarImages().stream()
                    .map(carImage -> s3Service.getUnSignedUrl(carImage.getSavedName()))
                    .toList();

            InputStream imageStream = getClass().getClassLoader().getResourceAsStream("logo/logo.jpeg");

            // Load the logo from a file or classpath
            BufferedImage logoImage = ImageIO.read(imageStream); // Update the path to your logo file

            // Create a new PDF document in memory
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PDDocument pdfDoc = new PDDocument();

            for (String imageUrl : imageUrls) {
                // Fetch the image from the URL
                BufferedImage bufferedImage = ImageIO.read(new URL(imageUrl));

                // Get original image width and height
                float imageWidth = bufferedImage.getWidth();
                float imageHeight = bufferedImage.getHeight();

                // Convert BufferedImage to InputStream
                ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", imageOutputStream); // Convert to JPEG format

                // Create a PDImageXObject from InputStream for car image
                PDImageXObject carImage = PDImageXObject.createFromByteArray(pdfDoc, imageOutputStream.toByteArray(), imageUrl);

                // Create a PDImageXObject from InputStream for logo
                ByteArrayOutputStream logoOutputStream = new ByteArrayOutputStream();
                ImageIO.write(logoImage, "jpeg", logoOutputStream); // Convert to JPEG format
                PDImageXObject logo = PDImageXObject.createFromByteArray(pdfDoc, logoOutputStream.toByteArray(), "logo");

                // Create a new page with the original car image size
                PDPage page = new PDPage(new PDRectangle(imageWidth, imageHeight));
                pdfDoc.addPage(page);

                // Draw the car image on the page
                PDPageContentStream contentStream = new PDPageContentStream(pdfDoc, page);
                contentStream.drawImage(carImage, 0, 0, imageWidth, imageHeight); // Position car image with original dimensions

                // Dynamic logo scaling: Ensure the logo size is a percentage of the image size
                float logoWidth = imageWidth * 0.15f; // Logo will be 15% of image width
                float logoHeight = (logoImage.getHeight() * logoWidth) / logoImage.getWidth(); // Scale logo height proportionally

                // Ensure the logo height doesn't exceed a reasonable portion of the image height
                if (logoHeight > imageHeight * 0.15f) {
                    logoHeight = imageHeight * 0.15f; // Logo will be at most 15% of image height
                    logoWidth = (logoImage.getWidth() * logoHeight) / logoImage.getHeight(); // Adjust width accordingly
                }

                // Calculate dynamic logo position based on the image size
                float logoXPosition = 20; // Fixed padding from the left
                float logoYPosition = imageHeight - logoHeight - 20; // Fixed padding from the top

                // Draw the logo on the image at the calculated position
                contentStream.drawImage(logo, logoXPosition, logoYPosition, logoWidth, logoHeight);

                contentStream.close();
            }

            // Write the PDF content to the output stream
            pdfDoc.save(out);
            pdfDoc.close();

            // Convert the output stream to an InputStreamResource
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=CarImages_" + id + ".pdf");

            // Return the PDF as a downloadable file or inline in the browser
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(in));
        } catch (NoSuchElementException e) {
            // Handle the case where the car is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new InputStreamResource(new ByteArrayInputStream(("Car not found with ID " + id).getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // Handle other exceptions
        }
    }





}
