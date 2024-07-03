export type State =
    | { type: 'loading' }
    | { type: 'success'; message: string }
    | { type: 'error'; message: string };
