package pt.isel.odin.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import pt.isel.odin.model.Category
import pt.isel.odin.model.Module
import pt.isel.odin.model.SubCategory
import pt.isel.odin.repository.CategoryRepository
import pt.isel.odin.repository.ModuleRepository
import pt.isel.odin.repository.SubCategoryRepository
import java.io.File

@Configuration
class DataInitializer {

    @Bean
    fun initData(
        categoryRepository: CategoryRepository,
        subCategoryRepository: SubCategoryRepository,
        moduleRepository: ModuleRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            if (categoryRepository.count() == 0L) {
                populateData(
                    categoryRepository,
                    subCategoryRepository,
                    moduleRepository
                )
            } else {
                println("Data already populated")
            }
        }
    }

    @Transactional
    fun populateData(
        categoryRepository: CategoryRepository,
        subCategoryRepository: SubCategoryRepository,
        moduleRepository: ModuleRepository
    ) {
        val mapper = jacksonObjectMapper()
        val data: InitialData =
            mapper.readValue(File("C:/Users/draga/Desktop/ISEL/6Semestre/PS/AssemblyOdin/code/backend/Odin/src/main/kotlin/pt/isel/odin/utils/CurricularUnitsData.json"))

        // Save categories and subcategories
        data.categories.forEach { categoryData ->
            val category = categoryRepository.save(Category(name = categoryData.name))
            categoryData.subcategories.forEach { subCategoryData ->
                val subCategory =
                    subCategoryRepository.save(SubCategory(category = category, name = subCategoryData.name))
                subCategoryData.modules.forEach { moduleData ->
                    moduleRepository.save(
                        Module(
                            subCategory = subCategory,
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
    val categories: List<CategoryData>
)

data class CategoryData(
    val name: String,
    val subcategories: List<SubCategoryData>
)

data class SubCategoryData(
    val name: String,
    val modules: List<ModuleData>
)

data class ModuleData(
    val name: String,
    val tier: Int
)
