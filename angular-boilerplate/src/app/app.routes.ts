import { Routes } from "@angular/router"
import { AuthGuard } from "./guards/auth.guard"

export const routes: Routes = [
  {
    path: "",
    loadComponent: () => import("./pages/home/home.component").then((m) => m.HomeComponent),
  },
  {
    path: "login",
    loadComponent: () => import("./pages/login/login.component").then((m) => m.LoginComponent),
  },
  {
    path: "signup",
    loadComponent: () => import("./pages/signup/signup.component").then((m) => m.SignupComponent),
  },
  {
    path: "users",
    loadComponent: () => import("./pages/users/users.component").then((m) => m.UsersComponent),
  },
  {
    path: "users/:id",
    loadComponent: () => import("./pages/user-profile/ user-profile.component").then((m) => m.UserProfileComponent),
    data: { renderMode: 'no-prerender' }
  },
  {
    path: "users/:id/edit",
    loadComponent: () => import("./pages/user-edit/user-edit.component").then((m) => m.UserEditComponent),
    canActivate: [AuthGuard],
    data: { renderMode: 'no-prerender' }
  },
  {
    path: "users/:id/following",
    loadComponent: () => import("./pages/show-follow/show-follow.component").then((m) => m.ShowFollowComponent),
    data: { type: "following", renderMode: 'no-prerender' },
  },
  {
    path: "users/:id/followers",
    loadComponent: () => import("./pages/show-follow/show-follow.component").then((m) => m.ShowFollowComponent),
    data: { type: "followers", renderMode: 'no-prerender' },
  },
  {
    path: "about",
    loadComponent: () => import("./pages/about/about.component").then((m) => m.AboutComponent),
  },
  {
    path: "contact",
    loadComponent: () => import("./pages/contact/contact.component").then((m) => m.ContactComponent),
  },
  {
    path: "account_activations/:token/edit",
    loadComponent: () =>
      import("./pages/account-activations/account-activations.component").then((m) => m.AccountActivationsComponent),
    data: { renderMode: 'no-prerender' }
  },
  {
    path: "account_activations/new",
    loadComponent: () =>
      import("./pages/account-activations-new/account-activations-new.component").then(
        (m) => m.AccountActivationsNewComponent,
      ),
  },
  {
    path: "password_resets/new",
    loadComponent: () =>
      import("./pages/password-resets-new/password-resets-new.component").then((m) => m.PasswordResetsNewComponent),
  },
  {
    path: "password_resets/:token",
    loadComponent: () =>
      import("./pages/password-resets/password-resets.component").then((m) => m.PasswordResetsComponent),
    data: { renderMode: 'no-prerender' }
  },
  {
    path: "**",
    loadComponent: () => import("./pages/not-found/not-found.component").then((m) => m.NotFoundComponent),
  },
]
