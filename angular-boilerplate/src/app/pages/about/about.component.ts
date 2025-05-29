import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"

@Component({
  selector: "app-about",
  standalone: true,
  imports: [CommonModule],
  template: `
    <h1>About</h1>
    <p>
      The <a href="https://angular.dev/"><em>Angular Tutorial</em></a>, part of the
      <a href="https://www.learnenough.com/">Learn Enough</a> family of tutorials,
      is a <a href="https://angular.dev/overview">guide</a> and
      <a href="https://www.youtube.com/angular">screencast series</a> to teach web
      development with <a href="https://angular.dev/">Angular</a>. This is the sample
      app for the tutorial.
    </p>
  `,
})
export class AboutComponent {}
