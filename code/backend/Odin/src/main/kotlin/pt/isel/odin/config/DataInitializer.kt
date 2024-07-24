package pt.isel.odin.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.Department
import pt.isel.odin.model.FieldStudy
import pt.isel.odin.model.Module
import pt.isel.odin.model.Role
import pt.isel.odin.repository.DepartmentRepository
import pt.isel.odin.repository.FieldStudyRepository
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.repository.RoleRepository
import java.io.File

@Configuration
@Profile("default")
class DataInitializer(
    private val dataPopulationService: DataPopulationService,
    private val rolePopulationService: RolePopulationService
) {

    @Bean
    fun initData(): CommandLineRunner {
        return CommandLineRunner {
            val dataFilePath =
                System.getenv("INITIAL_DATA_PATH") ?: "C:\\GitRepos\\AssemblyOdin\\code\\backend\\Odin\\src\\main\\kotlin\\pt\\isel\\odin\\utils\\InitialData.json"

            if (dataPopulationService.departmentRepository.count() == 0L) {
                val mapper = jacksonObjectMapper()
                val data: InitialData = mapper.readValue(File(dataFilePath))
                dataPopulationService.populateData(data)
            }
            if (rolePopulationService.roleRepository.count() == 0L) {
                val mapper = jacksonObjectMapper()
                val data: InitialData = mapper.readValue(File(dataFilePath))
                rolePopulationService.populateRoles(data)
            }
        }
    }
}

@Service
class DataPopulationService(
    val departmentRepository: DepartmentRepository,
    private val fieldStudyRepository: FieldStudyRepository,
    private val moduleRepository: ModuleRepository
) {

    @Transactional
    fun populateData(data: InitialData) {
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

@Service
class RolePopulationService(
    val roleRepository: RoleRepository
) {

    @Transactional
    fun populateRoles(data: InitialData) {
        data.roles.forEach { roleData ->
            roleRepository.save(Role(name = roleData.name))
        }
    }
}

data class InitialData(
    val departments: List<DepartmentData>,
    val roles: List<RoleData>
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

data class RoleData(
    val name: String
)
