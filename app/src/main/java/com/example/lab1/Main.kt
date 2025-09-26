package com.example.lab1
val students = mutableListOf<Student>()
val courses = mutableListOf<Course>()
val enrollments = mutableListOf<Enrollment>()
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

fun displayMenu() {
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
fun registerNewStudent() {
    println("\n--- Register New Student ---")
    val id = readNotEmptyLine("Enter Student ID (e.g., ST001): ").toUpperCase()
    if (students.any { it.id == id }) {
        println("ERROR: Student ID $id already exists.")
        return
    }

    val name = readNotEmptyLine("Enter Student Name: ")
    print("Enter Student Email (Optional, press Enter for none): ")
    val email = readLine()?.trim().takeIf { !it.isNullOrEmpty() }
    val major = readNotEmptyLine("Enter Student Major: ")

    val student = Student(id = id, name = name, email = email, major = major)
    students.add(student)


    println("\nSUCCESS: Student '${student.name}' ($id) registered!")
}

fun viewAllStudents() {
    println("\n--- All Registered Students ---")
    if (students.isEmpty()) {
        println("No students registered yet.")
        return
    }
    students.forEach { student ->
        val emailDisplay = student.email ?: "N/A"
        println("ID: ${student.id} | Name: ${student.name.padEnd(20)} | Major: ${student.major.padEnd(10)} | Email: $emailDisplay")
    }
}



fun createNewCourse() {
    println("\n--- Create New Course ---")

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

    courses.forEach { course ->
        val enrollmentCount = enrollments.count { it.courseId == course.courseId }
        println("ID: ${course.courseId} | Name: ${course.courseName.padEnd(30)} | Credits: ${course.credits} | Enrolled: $enrollmentCount students")
    }
}

fun enrollStudentInCourse() {
    println("\n--- Enroll Student in Course ---")
    val studentId = readNotEmptyLine("Enter Student ID to enroll: ").toUpperCase()
    val courseId = readNotEmptyLine("Enter Course ID for enrollment: ").toUpperCase()

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

    if (enrollments.any { it.studentId == studentId && it.courseId == courseId }) {
        println("\nNOTICE: Student '${student.name}' is already enrolled in '${course.courseName}'.")
        return
    }


    val enrollment = Enrollment(studentId = studentId, courseId = courseId)
    enrollments.add(enrollment)
    println("\nSUCCESS: ${student.name} successfully enrolled in ${course.courseName}!")
}

fun viewCourseStudents() {
    println("\n--- View Students by Course ---")
    val courseId = readNotEmptyLine("Enter Course ID to view enrolled students: ").toUpperCase()

    val course = courses.find { it.courseId == courseId }
    if (course == null) {
        println("\nERROR: Course with ID '$courseId' not found.") // Handle case when course doesn't exist [cite: 98]
        return
    }
    val courseEnrollments = enrollments.filter { it.courseId == courseId }

    println("\n--- Students enrolled in ${course.courseName} ($courseId) ---")

    if (courseEnrollments.isEmpty()) {
        println("No students are currently enrolled in this course.") // Handle case when course has no students [cite: 98]
        return
    }
    courseEnrollments.forEachIndexed { index, enrollment ->
        val student = students.find { it.id == enrollment.studentId }
        if (student != null) {
            println("${index + 1}. ID: ${student.id} | Name: ${student.name} | Major: ${student.major}")
        }
    }
}
fun main() {
    while (true) {
        displayMenu()
        val choice = readLine()?.trim()


        when (choice) {
            "1" -> registerNewStudent()
            "2" -> createNewCourse()
            "3" -> enrollStudentInCourse()
            "4" -> viewCourseStudents()
            "5" -> viewAllStudents()
            "6" -> viewAllCourses()
            "7", "exit" -> {
                println("\nThank you for using the Student Course Registration System. Goodbye! 👋")
                return
            }
            else -> {
                println("Invalid option '$choice'. Please type a number between 1 and 7, or 'exit'.")
            }
        }
    }
}