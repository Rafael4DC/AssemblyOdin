package pt.isel.odin.http.utils

import org.springframework.http.ResponseEntity
import java.net.URI

class Problem(
    val status: Int,
    typeUri: URI,
    title: String
) {
    val type = typeUri.toASCIIString()

    val title = title

    companion object {
        private const val MEDIA_TYPE = "application/problem+json"
        fun response(problem: Problem): ResponseEntity<Any> = ResponseEntity
            .status(problem.status)
            .header("Content-Type", MEDIA_TYPE)
            .body<Any>(problem)

        fun responseForError(any: Any) =
            response(
                errorToProblem.getOrDefault(
                    any,
                    unexpectedError
                )
            )

        private val userAlreadyExists = Problem(
            409,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/user-already-exists"
            ),
            "User already exists"
        )

        private val userNotFound = Problem(
            404,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/user-not-found"
            ),
            "User not found"
        )

        private val insecurePassword = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/insecure-password"
            ),
            "Insecure password"
        )

        private val userOrPasswordAreInvalid = Problem(
            401,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/user-or-password-are-invalid"
            ),
            "User or password are invalid"
        )

        val invalidRequestContent = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/invalid-request-content"
            ),
            "Invalid request content"
        )

        private val unexpectedError = Problem(
            500,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/unexpected-error"
            ),
            "Unexpected error"
        )

        private val sameUser = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/same-user"
            ),
            "Same user"
        )

        private val gameAlreadyExists = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/game-already-exists"
            ),
            "Game already exists"
        )

        private val invalidGameRules = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/invalid-game-rules"
            ),
            "Invalid game rules"
        )

        private val playerWaitingNotFound = Problem(
            404,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/player-waiting-not-found"
            ),
            "Player waiting not found"
        )

        private val userIdInvalid = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/user-id-invalid"
            ),
            "User id invalid"
        )

        private val gameRulesIdInvalid = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/game-rules-id-invalid"
            ),
            "Game rules id invalid"
        )

        private val gameIdInvalid = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/game-id-invalid"
            ),
            "Game id invalid"
        )

        private val gameNotFound = Problem(
            404,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/game-not-found"
            ),
            "Game not found"
        )

        private val unauthorized = Problem(
            401,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/unauthorized"
            ),
            "Unauthorized"
        )

        private val lobbyNotFound = Problem(
            404,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/lobby-not-found"
            ),
            "Lobby not found"
        )

        private val lobbyIdInvalid = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/lobby-id-invalid"
            ),
            "Lobby id invalid"
        )

        private val notYourTurn = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/not-your-turn"
            ),
            "Not your turn"
        )

        private val gameEnded = Problem(
            400,
            URI(
                "https://github.com/isel-leic-daw/2023-daw-leic51d-03/blob/main/code/docs/game-ended"
            ),
            "Game ended"
        )

        private val errorToProblem: HashMap<Any, Problem> = hashMapOf(
            /**
             * User
             */
            /*UserErrors.UserAlreadyExists to userAlreadyExists,
            UserErrors.InsecurePassword to insecurePassword,
            UserErrors.UserNotFound to userNotFound,

            *//**
             * Token
             *//*
            TokenErros.UserOrPasswordAreInvalid to userOrPasswordAreInvalid,

            *//**
             * Game
             *//*
            GameErrors.InvalidGameRules to invalidGameRules,
            GameErrors.PlayerWaitingNotFound to playerWaitingNotFound,
            GameErrors.SameUser to sameUser,
            GameErrors.GameAlreadyExists to gameAlreadyExists,
            GameErrors.UserNotFound to userNotFound,
            GameErrors.UserIdInvalid to userIdInvalid,
            GameErrors.GameRulesIdInvalid to gameRulesIdInvalid,
            GameErrors.GameIdInvalid to gameIdInvalid,
            GameErrors.GameNotFound to gameNotFound,
            GameErrors.Unauthorized to unauthorized,
            GameErrors.NotYourTurn to notYourTurn,
            GameErrors.GameEnded to gameEnded,

            *//**
             * Lobby
             *//*
            LobbyErrors.LobbyNotFound to lobbyNotFound,
            LobbyErrors.LobbyIdInvalid to lobbyIdInvalid,
            LobbyErrors.UserIdInvalid to userIdInvalid,
            LobbyErrors.Unauthorized to unauthorized*/
        )
    }
}
