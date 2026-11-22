package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.User
import pe.edu.pe.domain.repository.AuthRepository

class GetCurrentUserUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return repository.getCurrentUser()
    }
}
