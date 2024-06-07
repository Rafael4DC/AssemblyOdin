package pt.isel.odin.service

/**
 * Exception thrown when a resource is not found.
 *
 * @param msg the exception message.
 */
class NotFoundException(msg: String) : Exception(msg)
