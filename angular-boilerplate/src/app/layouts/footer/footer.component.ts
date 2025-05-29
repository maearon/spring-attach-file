import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"

@Component({
  selector: "app-footer",
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <footer class="footer mt-5 py-3 bg-light">
      <div class="container text-center">
        <div class="mb-2">
          <a routerLink="/about" class="me-2">About</a>
          <a routerLink="/contact" class="me-2">Contact</a>
          <a href="https://blog.angular.dev/meet-angular-v19-7b29dfd05b84" target="_blank" rel="noopener noreferrer">News</a>
        </div>
        <!-- <div>
          <a href="https://angular.dev/" target="_blank" rel="noopener noreferrer">
            <img src="/assets/images/angular.svg.png" alt="Angular logo" width="100" height="30">
          </a>
        </div> -->
      </div>
    </footer>
  `,
  styles: [
    `
    .footer {
      position: relative; /* Changed from absolute to relative */
      width: 100%;
      margin-top: 2rem;
    }
    a {
      color: #333;
      text-decoration: none;
    }
    a:hover {
      text-decoration: underline;
    }
  `,
  ],
})
export class FooterComponent {}
