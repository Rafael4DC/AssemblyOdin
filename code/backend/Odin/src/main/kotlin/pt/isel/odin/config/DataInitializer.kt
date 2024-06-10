package pt.isel.odin.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.repository.ModuleRepository
import java.io.File

@Configuration
@Profile("default")
class DataInitializer {

    @Bean
    fun initData(
        departmentRepository: DepartmentRepository,
        fieldStudyRepository: FieldStudyRepository,
        moduleRepository: ModuleRepository,
    ): CommandLineRunner {
        return CommandLineRunner {
            if (departmentRepository.count() == 0L) {
                populateData(
                    departmentRepository,
                    fieldStudyRepository,
                    moduleRepository
                )
            } else {
                println("Data already populated")
            }
        }
    }

    @Transactional
    fun populateData(
        departmentRepository: DepartmentRepository,
        fieldStudyRepository: FieldStudyRepository,
        moduleRepository: ModuleRepository
    ) {
        val mapper = jacksonObjectMapper()
        val data: InitialData =
            mapper.readValue(File("C:/Users/draga/Desktop/ISEL/6Semestre/PS/AssemblyOdin/code/backend/Odin/src/main/kotlin/pt/isel/odin/utils/CurricularUnitsData.json"))

        // Save categories and subcategories
        data.departments.forEach { categoryData ->
            val department = departmentRepository.save(Department(name = categoryData.name))
            categoryData.fieldsStudy.forEach { subCategoryData ->
                val fieldStudy =
                    fieldStudyRepository.save(FieldStudy(department = department, name = subCategoryData.name))
                subCategoryData.modules.forEach { moduleData ->
                    moduleRepository.save(
                        Module(
                            fieldStudy = fieldStudy,
                            name = moduleData.name,
                            tier = moduleData.tier
                        )
                    )
                }
            }
        }
    }
}

data class InitialData(
    val departments: List<DepartmentData>
)

data class DepartmentData(
    val name: String,
    val fieldsStudy: List<FieldStudyData>
)

data class FieldStudyData(
    val name: String,
    val modules: List<ModuleData>
)

data class ModuleData(
    val name: String,
    val tier: Int
)
