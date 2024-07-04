package pt.isel.odin.utils

/**
 * Either type
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>()
    data class Right<out R>(val value: R) : Either<Nothing, R>()
}

/**
 * Creates a [Either.Right] type.
 * @param value The value to be stored in the [Either.Right].
 */
fun <R> success(value: R) = Either.Right(value)

/**
 * Creates a [Either.Left] type.
 * @param error The error to be stored in the [Either.Left].
 */
fun <L> failure(error: L) = Either.Left(error)

/**
 * Represents Success
 */
typealias Success<S> = Either.Right<S>

/**
 * Represents Failure
 */
typealias Failure<F> = Either.Left<F>
