

function to<T, E = Error>(promise: Promise<T>): Promise<[E, null] | [null, T]> {
  return promise
    .then<[null, T]>(data => [null, data])
    .catch<[E, null]>(err => [err, null])
}

export async function get<T>(input: RequestInfo | URL): Promise<T> {
  return await apiFetch<T>(input, 'GET')
}

async function apiFetch<T>(
  input: RequestInfo | URL,
  method?: string,
  body?: BodyInit,
  headers?: HeadersInit,
): Promise<T> {
  const [err, res] = await to<Response>(fetch(input, {
    method,
    headers: headers ? headers : {
      'Content-Type': 'application/json',
    },
    body
  }))

  if (err) {
    console.log("API fetch error:", err, res)
    //throw new NetworkError(err.message)
  }

  if (!res.ok) {
    //if (res?.headers.get('Content-Type') !== problemMediaType) {
      // console.log("API fetch non-Problem+JSON error:", res)
      //throw new UnexpectedResponseError(`Unexpected response type: ${res.headers.get('Content-Type')}`)
    //}

    console.log("API fetch Problem+JSON:", res)
    //throw new Problem(await res.json())
  }
  if (res?.headers.get('Content-Type') !== 'application/json')
    //throw new UnexpectedResponseError(`Unexpected response type: ${res.headers.get('Content-Type')}`)

  return await res.json()
}