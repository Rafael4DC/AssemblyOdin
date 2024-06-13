package pt.isel.odin.utils

import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module
import pt.isel.odin.model.Role
import pt.isel.odin.model.Section
import pt.isel.odin.model.Tech
import pt.isel.odin.model.Voc
import pt.isel.odin.model.user.User
import java.time.LocalDateTime

object TestData {
    val department1 = Department(name = "Engineering")
    val department2 = Department(name = "Science")
    val department3 = Department(name = "Arts")
    val department4 = Department(name = "Business")
    val department5 = Department(name = "Law")

    val fieldStudy1 = FieldStudy(name = "Software Engineering", department = department1)
    val fieldStudy2 = FieldStudy(name = "Physics", department = department2)
    val fieldStudy3 = FieldStudy(name = "History", department = department3)
    val fieldStudy4 = FieldStudy(name = "Management", department = department4)
    val fieldStudy5 = FieldStudy(name = "Criminal Law", department = department5)

    val module1 = Module(name = "Algorithms", fieldStudy = fieldStudy1, tier = 2)
    val module2 = Module(name = "Quantum Mechanics", fieldStudy = fieldStudy2, tier = 3)
    val module3 = Module(name = "Ancient History", fieldStudy = fieldStudy3)
    val module4 = Module(name = "Modern History", fieldStudy = fieldStudy3)
    val module5 = Module(name = "Marketing", fieldStudy = fieldStudy4)
    val module6 = Module(name = "Forensic Science", fieldStudy = fieldStudy5)

    val section1 = Section(name = "Introduction", summary = "This is the introduction section.", module = module1)
    val section2 = Section(name = "History", summary = "This section covers history.", module = module2)
    val section3 = Section(name = "Part 1", summary = "This is part 1.", module = module3)
    val section4 = Section(name = "Part 2", summary = "This is part 2.", module = module4)
    val section5 = Section(name = "Old Name", summary = "Old summary.", module = module5)
    val section6 = Section(name = "To be deleted", summary = "This section will be deleted.", module = module6)
    val section7 = Section(name = "Science", summary = "This section covers science.", module = module6)

    val role1 = Role(name = "Admin")
    val role2 = Role(name = "User")
    val role3 = Role(name = "Manager")
    val role4 = Role(name = "Developer")
    val role5 = Role(name = "Tester")
    val role6 = Role(name = "Support")
    val role7 = Role(name = "Operations")

    val user1 = User(email = "admin@example.com", username = "admin", role = role1, credits = 100)
    val user2 = User(email = "duplicate@example.com", username = "user1", role = role2)
    val user3 = User(email = "duplicate11@example.com", username = "user2", role = role2)
    val user4 = User(email = "manager@example.com", username = "manager", role = role3)
    val user5 = User(email = "employee1@example.com", username = "employee1", role = role4)
    val user6 = User(email = "employee2@example.com", username = "employee2", role = role4)
    val user7 = User(email = "support@example.com", username = "support", role = role5)
    val user8 = User(email = "operations@example.com", username = "operations", role = role6)


    val tech1 = Tech(teacher = user1, section = section1, date = LocalDateTime.now(), summary = "Tech summary")
    val tech2 = Tech(teacher = user1, section = section2, date = LocalDateTime.now(), summary = "Algebra Tech summary")
    val tech3 = Tech(teacher = user1, section = section3, date = LocalDateTime.now(), summary = "Physics Tech 1")
    val tech4 = Tech(teacher = user1, section = section4, date = LocalDateTime.now(), summary = "Physics Tech 2")
    val tech5 = Tech(teacher = user1, section = section5, date = LocalDateTime.now(), summary = "Botany Tech")
    val tech6 = Tech(teacher = user1, section = section6, date = LocalDateTime.now(), summary = "Organic Chemistry Tech")
    val tech7 = Tech(
        teacher = user1,
        section = section7,
        date = LocalDateTime.now(),
        summary = "History Tech",
        missTech = mutableListOf(user2, user3)
    )

    val voc1 = Voc(
        description = "Voc description",
        approved = true,
        user = user4,
        section = section1,
        started = LocalDateTime.now(),
        ended = LocalDateTime.now().plusDays(1)
    )
    val voc2 = Voc(
        description = "Algebra Voc",
        approved = true,
        user = user4,
        section = section2,
        started = LocalDateTime.now(),
        ended = LocalDateTime.now().plusDays(1)
    )
    val voc3 = Voc(
        description = "Physics Voc 1",
        approved = true,
        user = user4,
        section = section3,
        started = LocalDateTime.now(),
        ended = LocalDateTime.now().plusDays(1)
    )
    val voc4 = Voc(
        description = "Physics Voc 2",
        approved = false,
        user = user4,
        section = section4,
        started = LocalDateTime.now(),
        ended = LocalDateTime.now().plusDays(2)
    )
    val voc5 = Voc(
        description = "Botany Voc",
        approved = true,
        user = user4,
        section = section5,
        started = LocalDateTime.now(),
        ended = LocalDateTime.now().plusDays(1)
    )
    val voc6 = Voc(
        description = "History Voc",
        approved = true,
        user = user4,
        section = section6,
        started = LocalDateTime.now(),
        ended = LocalDateTime.now().plusDays(1)
    )


    val nonExistentId = 999L
    val negativeId = -1L
}
