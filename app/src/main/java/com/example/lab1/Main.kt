package com.example.lab1

// File 2: Main.kt

// --- Global Data Storage (Keep lists of students and courses in memory [cite: 71]) ---
val students = mutableListOf<Student>()
val courses = mutableListOf<Course>()
val enrollments = mutableListOf<Enrollment>()

// A simple utility function to read non-empty input
fun readNotEmptyLine(prompt: String): String {
    var input: String? = null
    while (input.isNullOrBlank()) {
        print(prompt)
        input = readLine()
        if (input.isNullOrBlank()) {
            println("Input cannot be empty. Please try again.")
        }
    }
    return input.trim()
}

// --- Part 2: Main Menu System [cite: 68] ---

fun displayMenu() {
    // Create a clean, professional-looking menu [cite: 72, 73, 74]
    println("\n=============================================")
    println("=== Student Course Registration System ===")
    println("=============================================")
    println("1. Register new student")
    println("2. Create new course")
    println("3. Enroll student in course")
    println("4. View students in a course")
    println("5. View all students")
    println("6. View all courses (with enrollment count)")
    println("7. Exit")
    println("---------------------------------------------")
    print("Your selection (1-7): ")
}

// --- Part 3: Student Management [cite: 75] ---

fun registerNewStudent() {
    println("\n--- Register New Student ---")
    // Ask user for student details step by step [cite: 77, 35]
    val id = readNotEmptyLine("Enter Student ID (e.g., ST001): ").toUpperCase()
    if (students.any { it.id == id }) {
        println("ERROR: Student ID $id already exists.")
        return
    }

    val name = readNotEmptyLine("Enter Student Name: ")
    print("Enter Student Email (Optional, press Enter for none): ")
    val email = readLine()?.trim().takeIf { !it.isNullOrEmpty() }
    val major = readNotEmptyLine("Enter Student Major: ")

    // Create Student object and add to students list [cite: 77]
    val student = Student(id = id, name = name, email = email, major = major)
    students.add(student)

    // Show success message [cite: 77, 33]
    println("\nSUCCESS: Student '${student.name}' ($id) registered!")
}

fun viewAllStudents() {
    println("\n--- All Registered Students ---")
    if (students.isEmpty()) {
        println("No students registered yet.")
        return
    }

    // Display all registered students in a nice format [cite: 79, 80]
    students.forEach { student ->
        val emailDisplay = student.email ?: "N/A"
        println("ID: ${student.id} | Name: ${student.name.padEnd(20)} | Major: ${student.major.padEnd(10)} | Email: $emailDisplay")
    }
}

// --- Part 4: Course Management [cite: 81] ---

fun createNewCourse() {
    println("\n--- Create New Course ---")
    // Ask for course ID, name, and credits [cite: 84, 36]
    val courseId = readNotEmptyLine("Enter Course ID (e.g., CS101): ").toUpperCase()
    if (courses.any { it.courseId == courseId }) {
        println("ERROR: Course ID $courseId already exists.")
        return
    }

    val courseName = readNotEmptyLine("Enter Course Name: ")
    var credits: Int? = null
    while (credits == null) {
        print("Enter Course Credits (e.g., 3): ")
        credits = readLine()?.toIntOrNull()
        if (credits == null || credits <= 0) {
            println("Invalid credit amount. Please enter a positive number.")
            credits = null
        }
    }

    // Add to courses list [cite: 85, 37]
    val course = Course(courseId = courseId, courseName = courseName, credits = credits)
    courses.add(course)

    println("\nSUCCESS: Course '${course.courseName}' ($courseId) created!")
}

fun viewAllCourses() {
    println("\n--- All Courses ---")
    if (courses.isEmpty()) {
        println("No courses created yet.")
        return
    }

    // Show all courses with enrollment numbers [cite: 87]
    courses.forEach { course ->
        val enrollmentCount = enrollments.count { it.courseId == course.courseId }
        println("ID: ${course.courseId} | Name: ${course.courseName.padEnd(30)} | Credits: ${course.credits} | Enrolled: $enrollmentCount students")
    }
}

// --- Part 5: Enrollment System [cite: 88] ---

fun enrollStudentInCourse() {
    println("\n--- Enroll Student in Course ---")
    // Ask user for student ID and course ID [cite: 90]
    val studentId = readNotEmptyLine("Enter Student ID to enroll: ").toUpperCase()
    val courseId = readNotEmptyLine("Enter Course ID for enrollment: ").toUpperCase()

    // Find the student and course to verify they exist [cite: 91]
    val student = students.find { it.id == studentId }
    val course = courses.find { it.courseId == courseId }

    if (student == null) {
        println("\nERROR: Student with ID '$studentId' not found.")
        return
    }
    if (course == null) {
        println("\nERROR: Course with ID '$courseId' not found.")
        return
    }

    // Check if enrollment already exists
    if (enrollments.any { it.studentId == studentId && it.courseId == courseId }) {
        println("\nNOTICE: Student '${student.name}' is already enrolled in '${course.courseName}'.")
        return
    }

    // Create an Enrollment object and add to the list [cite: 92, 93]
    val enrollment = Enrollment(studentId = studentId, courseId = courseId)
    enrollments.add(enrollment)

    // Show success message [cite: 94]
    println("\nSUCCESS: ${student.name} successfully enrolled in ${course.courseName}!")
}

fun viewCourseStudents() {
    println("\n--- View Students by Course ---")
    // Ask which course to view [cite: 96]
    val courseId = readNotEmptyLine("Enter Course ID to view enrolled students: ").toUpperCase()

    // Verify course exists
    val course = courses.find { it.courseId == courseId }
    if (course == null) {
        println("\nERROR: Course with ID '$courseId' not found.") // Handle case when course doesn't exist [cite: 98]
        return
    }

    // Filter enrollments to find all for that course ID [cite: 97]
    val courseEnrollments = enrollments.filter { it.courseId == courseId }

    println("\n--- Students enrolled in ${course.courseName} ($courseId) ---")

    if (courseEnrollments.isEmpty()) {
        println("No students are currently enrolled in this course.") // Handle case when course has no students [cite: 98]
        return
    }

    // For each enrollment, find and display the student details [cite: 98]
    courseEnrollments.forEachIndexed { index, enrollment ->
        val student = students.find { it.id == enrollment.studentId }
        // Should always find the student if logic is sound
        if (student != null) {
            println("${index + 1}. ID: ${student.id} | Name: ${student.name} | Major: ${student.major}")
        }
    }
}

// --- Main Function (App Flow) [cite: 69] ---

fun main() {
    // Show menu until user exits [cite: 70]
    while (true) {
        displayMenu()
        val choice = readLine()?.trim()

        // Use when expression to handle user choices [cite: 71]
        when (choice) {
            "1" -> registerNewStudent() // Register new student [cite: 11]
            "2" -> createNewCourse()    // Create new course [cite: 12]
            "3" -> enrollStudentInCourse() // Enroll student [cite: 13]
            "4" -> viewCourseStudents() // View course students [cite: 14]
            "5" -> viewAllStudents()    // View all students [cite: 15]
            "6" -> viewAllCourses()     // View all courses [cite: 16]
            "7", "exit" -> { // Exit the application [cite: 17, 20]
                println("\nThank you for using the Student Course Registration System. Goodbye! 👋")
                return // Exits the main function, terminating the app
            }
            else -> {
                println("Invalid option '$choice'. Please type a number between 1 and 7, or 'exit'.")
            }
        }
        // App returns to menu after action [cite: 30]
    }
}