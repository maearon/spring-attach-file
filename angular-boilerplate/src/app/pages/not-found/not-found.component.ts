import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"

@Component({
  selector: "app-not-found",
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="text-center py-5">
      <h1 class="display-1 mb-4">404</h1>
      <h2 class="mb-4">Page Not Found</h2>
      <p class="mb-4">The page you are looking for does not exist or has been moved.</p>
      <a routerLink="/" class="btn btn-primary">Go to Home</a>
    </div>
  `,
})
export class NotFoundComponent {}
