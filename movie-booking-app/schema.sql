-- Using InnoDB engine for transaction support and foreign key constraints
SET default_storage_engine=InnoDB;

-- -----------------------------------------------------
-- Table `Members` (訂票用戶)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Members` (
  `member_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Uid for the member',
  `email` VARCHAR(255) NOT NULL UNIQUE COMMENT 'Email address',
  `password_hash` VARCHAR(255) NOT NULL COMMENT 'Hashed password',
  `date_of_birth` DATE NOT NULL COMMENT 'Date of birth, used for age verification against movie ratings',
  PRIMARY KEY (`member_id`),
  INDEX `idx_email` (`email`)
) COMMENT = 'Stores information about registered users/customers who book tickets.';

-- -----------------------------------------------------
-- Table `Movies` (電影)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movies` (
  `movie_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for the movie (uid)',
  `title` VARCHAR(255) NOT NULL COMMENT 'Name of the movie',
  `synopsis` TEXT COMMENT 'Brief description of the movie',
  `duration_minutes` INT UNSIGNED NOT NULL COMMENT 'Length of the movie in minutes',
  `rating_category` ENUM('普遍級', '保護級', '限制級') NOT NULL COMMENT 'Movie rating. Used for age checks.',
  -- `release_date` DATE COMMENT 'The official release date of the movie',
  -- `is_active` BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'Indicates if the movie is currently available',
  PRIMARY KEY (`movie_id`),
  INDEX `idx_title` (`title`)
) COMMENT = 'Stores details about each movie.';

-- -----------------------------------------------------
-- Table `Halls` (放映廳)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Halls` (
  `hall_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for the hall (uid)',
  -- `name` VARCHAR(100) NOT NULL UNIQUE COMMENT 'Name of the hall, e.g., Hall 1, IMAX Hall',
  `hall_type` VARCHAR(50) COMMENT 'Type of hall, e.g., Large, Small',
  `total_seats` INT UNSIGNED NOT NULL COMMENT 'Total number of seats in the hall',
  PRIMARY KEY (`hall_id`)
) COMMENT = 'Stores information about the cinema halls.';

-- -----------------------------------------------------
-- Table `Seats` (座位)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Seats` (
  `seat_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Uid for a seat',
  `hall_id` INT UNSIGNED NOT NULL COMMENT 'Foreign key referencing the hall this seat belongs to',
  `row_char` VARCHAR(1) NOT NULL COMMENT 'Seat row id (e.g., A, B, C)',
  `seat_number_in_row` INT UNSIGNED NOT NULL COMMENT 'Seat number within the row (e.g., 1, 2, 10)',
  PRIMARY KEY (`seat_id`),
  UNIQUE INDEX `uq_hall_row_number` (`hall_id`, `row_char`, `seat_number_in_row`),
  FOREIGN KEY (`hall_id`) REFERENCES `Halls` (`hall_id`) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT = 'Defines every seats within each hall, including their location.';

-- -----------------------------------------------------
-- Table `Showtimes` (上映資訊)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Showtimes` (
  `showtime_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Uid for a specific showtime',
  `movie_id` INT UNSIGNED NOT NULL COMMENT 'Foreign key referencing the movie being shown',
  `hall_id` INT UNSIGNED NOT NULL COMMENT 'Foreign key referencing the hall where the movie is shown',
  `start_time` DATETIME NOT NULL COMMENT 'Date and time the movie screening starts',
  `end_time` DATETIME NOT NULL COMMENT 'Date and time the movie screening ends (calculated from start_time + movie_duration)',
  PRIMARY KEY (`showtime_id`),
  INDEX `idx_movie_id` (`movie_id`),
  INDEX `idx_hall_id` (`hall_id`),
  INDEX `idx_start_time` (`start_time`),
  FOREIGN KEY (`movie_id`) REFERENCES `Movies` (`movie_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (`hall_id`) REFERENCES `Halls` (`hall_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) COMMENT = 'Stores information about specific movie screenings.';

-- -----------------------------------------------------
-- Table `Bookings` (訂票資訊, Booking con contain multiple seats)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Bookings` (
  `booking_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Uid for the booking',
  `member_id` INT UNSIGNED NOT NULL COMMENT 'Foreign key referencing the member who made the booking',
  `showtime_id` INT UNSIGNED NOT NULL COMMENT 'Foreign key referencing the specific showtime booked',
  -- `booking_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp of when the booking was made',
  `status` ENUM('CONFIRMED', 'CANCELLED', 'PENDING') NOT NULL DEFAULT 'PENDING' COMMENT 'Status of the booking (e.g., Confirmed, Cancelled by user, Pending payment)',
  -- `total_price` DECIMAL(10, 2) COMMENT 'Total price for this booking',
  PRIMARY KEY (`booking_id`),
  INDEX `idx_member_id` (`member_id`),
  INDEX `idx_showtime_id` (`showtime_id`),
  FOREIGN KEY (`member_id`) REFERENCES `Members` (`member_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (`showtime_id`) REFERENCES `Showtimes` (`showtime_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) COMMENT = 'Stores the main information for each ticket booking.';

-- -----------------------------------------------------
-- Table `Booked_Tickets` (訂票座位)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Booked_Tickets` (
  `booked_ticket_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for a booked ticket/seat',
  `booking_id` INT UNSIGNED NOT NULL COMMENT 'Foreign key referencing the overall booking',
  `seat_id` INT UNSIGNED NOT NULL COMMENT 'Foreign key referencing the specific seat booked',
  `ticket_type` VARCHAR(50) NOT NULL DEFAULT 'Adult' COMMENT 'Type of ticket, e.g., Adult, Child, Senior',
  -- `price_at_booking` DECIMAL(8, 2) NOT NULL COMMENT 'Price of this specific ticket at the time of booking',
  PRIMARY KEY (`booked_ticket_id`),
  INDEX `idx_booking_id` (`booking_id`),
  INDEX `idx_seat_id` (`seat_id`),
  UNIQUE INDEX `uq_showtime_seat` (`booking_id`, `seat_id`) COMMENT 'Ensures a seat is booked only once per booking. Overall showtime-seat uniqueness is managed by application logic by checking existing Booked_Tickets for a showtime.',
  -- To enforce that a seat_id cannot be booked twice for the same showtime across different bookings,
  -- this unique constraint should ideally reference showtime_id as well.
  -- However, showtime_id is in the parent Bookings table.
  -- This will primarily be an application-level check: "Is seat X for showtime Y already in Booked_Tickets?"
  FOREIGN KEY (`booking_id`) REFERENCES `Bookings` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`seat_id`) REFERENCES `Seats` (`seat_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) COMMENT = 'Stores details of each individual seat/ticket part of a booking.';

-- -----------------------------------------------------
-- Table `Staff` (營運人員)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Staff` (
  `staff_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Uid for staff member',
  `username` VARCHAR(100) NOT NULL UNIQUE COMMENT 'Username for staff login',
  `password_hash` VARCHAR(255) NOT NULL COMMENT 'Hashed password for staff',
  `is_active` BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (`staff_id`),
  INDEX `idx_username` (`username`)
) COMMENT = 'Stores information for operational staff managing the cinema system.';
