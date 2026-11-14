
package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.User
import pe.edu.pe.domain.repository.AuthRepository

class GoogleLoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        idToken: String
    ): Result<User> {

        return repository.googleLogin(
            idToken
        )

    }
}
