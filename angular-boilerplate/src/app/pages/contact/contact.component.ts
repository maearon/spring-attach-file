import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"

@Component({
  selector: "app-contact",
  standalone: true,
  imports: [CommonModule],
  template: `
    <h1>Contact</h1>
    <p>
      Contact the Angular Tutorial about the sample app at the
      <a href="https://angular.dev/tutorials">contact page</a>.
    </p>
  `,
})
export class ContactComponent {}
