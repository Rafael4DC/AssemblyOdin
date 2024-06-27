/**
 * CreditLog model
 *
 * @param id the id of the credit log
 * @param description the description of the credit log
 * @param value the value of the credit log
 * @param date date that the credit log was created
 */
export interface CreditLog {
    id?: number
    description?: string
    value: number
    date: string,
}