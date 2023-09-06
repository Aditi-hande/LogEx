# LEAS

## ELK Installation: 
You can follow the installation steps for ELK stack from 
[ELK installation](https://www.digitalocean.com/community/tutorials/how-to-install-elasticsearch-logstash-and-kibana-elastic-stack-on-ubuntu-18-04)

# Configuration:
The logstash pipeline configuration files are available at ```/etc/logstash/conf.d/``` .
The file has following file structure 
```
input {
    file {
        path => "<location of log file>"
        type => "<unique identifier for your log file>"
    }
    file {
        ...   
    }
    ...
    ...
    ...
}
 filter { 
    if [type] == "type"{
        grok {
              <Specify the required grok parser>
         }
    }
    ...
    ...
    ...
}
output {
  stdout { codec => rubydebug } ## This outputs the parsed log file to stdout
	if [type] == "type" {
		elasticsearch {
			hosts => "<elasticsearch host address>"
			index => "<name for index>"
      username => "<username>"
      password => "<password>"
		}
	}  
	...
  ...
  ...
}
```


