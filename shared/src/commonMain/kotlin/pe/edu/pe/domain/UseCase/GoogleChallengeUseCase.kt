
package pe.edu.pe.domain.UseCase

import pe.edu.pe.domain.repository.AuthRepository

class GoogleChallengeUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): Result<String> {

        return repository.googleChallenge()

    }
}
