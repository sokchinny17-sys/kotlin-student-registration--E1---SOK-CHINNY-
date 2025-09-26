package com.example.lab1

// File 1: Models.kt (Contains your data classes/blueprints [cite: 48, 49, 50])

/**
 * Part 1: Data Models - Student [cite: 58]
 * id: String - Unique identifier (e.g., "ST001") [cite: 60]
 * name: String - Student's name [cite: 60]
 * email: String? - Optional email (use nullable type with ?) [cite: 61]
 * major: String - Student's field of study [cite: 61]
 */
data class Student(
    val id: String,
    val name: String,
    val email: String?,
    val major: String
)

/**
 * Part 1: Data Models - Course [cite: 62]
 * courseId: String - Unique identifier (e.g., "CS101") [cite: 63]
 * courseName: String - Full course name [cite: 64]
 * credits: Int - Number of credits for the course [cite: 65]
 */
data class Course(
    val courseId: String,
    val courseName: String,
    val credits: Int
)

/**
 * Part 1: Data Models - Enrollment [cite: 66]
 * studentId: String - ID of the enrolled student [cite: 67]
 * courseId: String - ID of the course student is enrolled in [cite: 67]
 */
data class Enrollment(
    val studentId: String,
    val courseId: String
)