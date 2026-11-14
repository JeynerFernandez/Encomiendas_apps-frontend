
package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.model.User
import pe.edu.pe.domain.repository.AuthRepository

class GoogleLinkUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        idToken: String,
        password: String
    ): Result<User> {

        return repository.googleLink(
            idToken,
            password
        )

    }
}
