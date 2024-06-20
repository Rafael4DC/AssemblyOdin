/**
 * Either type for handling success or error
 *
 * @param L - The error type
 * @param R - The success type
 */
export type Either<L, R> = Failure<L> | Success<R>;

/**
 * Success type
 *
 * @param value The value to be stored in the success type
 */
export function success<R>(value: R): Either<never, R> {
    return new Success<R>(value);
}

/**
 * Failure type
 *
 * @param error The error to be stored in the failure type
 */
export function failure<L>(error: L): Either<L, never> {
    return new Failure<L>(error);
}

/**
 * Represents Failure
 */
export class Failure<L> {
    constructor(public value: L) {}
}

/**
 * Represents Success
 */
export class Success<R> {
    constructor(public value: R) {}
}