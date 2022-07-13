package com.daniel.finalassignment.domain

import com.daniel.finalassignment.model.characters.CharactersItem

data class DomainCharacter(

    val id: Int,
    val name: String,
    val nickname: String,
    val occupation: List<String>,
    val status: String,
    val actor: String,
    val img: String

)

fun CharactersItem.mapToDomainCharacter(): DomainCharacter {
    return DomainCharacter(
        id = this.charId ?: 0,
        name = this.name ?: "No name available",
        nickname = this.nickname ?: "No nickname available",
        occupation = this.occupation,
        status = this.status ?: "No status available",
        actor = this.portrayed ?: "No actor available",
        img = this.img ?: "No image available"
    )
}

fun List<CharactersItem>.mapToDomainCharacters(): List<DomainCharacter> {

    return this.map { character ->
        DomainCharacter(
            id = character.charId ?: 0,
            name = character.name ?: "No name available",
            nickname = character.nickname ?: "No nickname available",
            occupation = character.occupation,
            status = character.status ?: "No status available",
            actor = character.portrayed ?: "No actor available",
            img = character.img ?: "No image available"
        )
    }

}
