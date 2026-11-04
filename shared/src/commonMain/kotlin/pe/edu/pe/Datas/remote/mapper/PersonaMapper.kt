package pe.edu.pe.Datas.remote.mapper

import pe.edu.pe.Datas.remote.dto.PersonaDto
import pe.edu.pe.domain.model.Persona


fun PersonaDto.toDomain(): Persona {

    return Persona(

        id =
            id,

        dni =
            dni,

        nombres =
            nombres,

        apellidoPaterno =
            apellidoPaterno,

        apellidoMaterno =
            apellidoMaterno,

        active =
            active
    )
}