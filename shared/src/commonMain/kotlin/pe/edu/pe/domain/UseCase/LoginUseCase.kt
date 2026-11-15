package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.User
import pe.edu.pe.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String
    ):Result<User>{
        return repository.login(email, password)
    }
}