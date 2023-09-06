import { ThrowStmt } from '@angular/compiler';

export class LogLine {
    _id: string ;
	host: string ;
    version: string;
    path: string;
	timestamp: string;
	type: string ;
	LEVEL: string ;
    detail: string ;
    message: string ;
    year: string;
    month: string;
    day: string;
    time: string;

    constructor(json:any){
        this._id = json._id;
        this.host = json.host ;
        this.version = json["@version"];
        this.path =  json.path;
        this.timestamp =  json["@timestamp"];
        this.time  = json.time ;
        this.LEVEL = json.LEVEL ;
        this.detail  = json.detail ;
        this.message  = json.message ;
        this.year  =  json.year;
        this.month =  json.month;
        this.day = json.day;
        this.time =  json.time;

    }
  
}
