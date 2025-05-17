import API from ".";

export interface CreateParams {
  followed_id: string | string[] | undefined
}

export interface CreateResponse {
  follow: boolean
}

export interface DestroyResponse {
  unfollow: boolean
}

const relationshipApi = {
  create(params: CreateParams): Promise<CreateResponse> {
    const url = `/relationships/${params.followed_id}/follow`;
    return API.post(url, params);
  },

  destroy(id: string): Promise<DestroyResponse> {
    const url = `/relationships/${id}/unfollow`;
    return API.delete(url);
  },
};

export default relationshipApi;
