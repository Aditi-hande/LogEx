import { Mask } from "./mask";
import { Schedule } from "./schedule";
import { Target } from "./target";
export class Query {
    
    product: string;
    queryName: string;
    description: string;
    queryText: string;
    elasticSearchIndex:String;
    maskList: String[];
    targetName:String[];
    scheduleName:String[];
}
