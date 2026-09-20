import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class ConvertDataUtilsService {
  //Example : input : "[001,002,003]"
  convertStrArrayToArray(input: string) {
    return input.slice(1, -1).split(',');
  }
}
