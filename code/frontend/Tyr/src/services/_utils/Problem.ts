/**
 * Problem class
 *
 * @property status the HTTP status code
 * @property title A short, human-readable summary of the problem type.
 * @property type a URI that identifies the problem type
 */
export class Problem {
    status?: number;
    title: string;
    type: string;

    constructor(problem: InputProblem) {
        this.status = problem.status;
        this.title = problem.title;
        this.type = problem.type;
    }
}

interface InputProblem {
    status?: number
    title: string
    type: string
}

export const problemMediaType = "application/problem+json"
