import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-contato',
  templateUrl: './contato.component.html',
  styleUrls: ['./contato.component.scss']
})
export class ContatoComponent implements OnInit {

  constructor(
    private titleService: Title
  ) { 
    this.titleService.setTitle("Contato - Agricolab");
  }
  
  ngOnInit(): void {
  }

}
