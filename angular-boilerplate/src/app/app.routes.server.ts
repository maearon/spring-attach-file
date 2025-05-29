import { RenderMode, ServerRoute } from '@angular/ssr';

// Example user IDs for prerendering; replace with your actual data source as needed
const userIds = ['1', '2', '3'];

// Example tokens for prerendering; replace with your actual data source as needed
const tokens = ['token1', 'token2', 'token3'];

export const serverRoutes: ServerRoute[] = [
  {
    path: 'users/:id',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return userIds.map(id => ({ id }));
    }
  },
  {
    path: 'users/:id/edit',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return userIds.map(id => ({ id }));
    }
  },
  {
    path: 'users/:id/following',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return userIds.map(id => ({ id }));
    }
  },
  {
    path: 'users/:id/followers',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return userIds.map(id => ({ id }));
    }
  },
  {
    path: 'account_activations/:token/edit',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return tokens.map(token => ({ token }));
    }
  },
  {
    path: 'password_resets/:token',
    renderMode: RenderMode.Prerender,
    async getPrerenderParams() {
      return tokens.map(token => ({ token }));
    }
  },
  {
    path: '**',
    renderMode: RenderMode.Prerender
  }
];
