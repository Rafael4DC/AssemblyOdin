interface Voc {
    id: number,
    description: string,
    approved: boolean,
    student: Student?,
    curricularUnit: CurricularUnit?,
    started: number,
    ended: number
}