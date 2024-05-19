import * as React from "react";

/**
 * AlertError props
 *
 * @param error - Error object
 */
interface AlertError {
    error: Error;
}

/**
 * AlertError component
 */
export function AlertError({error}: AlertError) {
    return (
        <div className="alert alert-danger" role="alert">Error: {error.message}</div>
    );
}