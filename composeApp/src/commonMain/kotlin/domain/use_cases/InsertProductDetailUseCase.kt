package domain.use_cases

import domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InsertProductDetailUseCase : KoinComponent {

    private val mainRepository: MainRepository by inject()

    operator fun invoke(id: Int, title: String, image: String, desc: String) = flow<Unit> {
        mainRepository.insert(id, title, desc, image)
    }.flowOn(Dispatchers.IO)
}