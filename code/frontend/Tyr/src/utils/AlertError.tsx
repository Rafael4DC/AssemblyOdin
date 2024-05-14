import * as React from "react";

interface AlertError {
    error: Error;
}

export function AlertError({error}: AlertError) {
    return (
        <div className="alert alert-danger" role="alert">Error: {error.message}</div>
    );
}