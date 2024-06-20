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

    constructor(type: string, title: string, status?: number) {
        this.status = status;
        this.title = title;
        this.type = type;
    }
}