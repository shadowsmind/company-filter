Companies-filter
=============

### Description

It's a test task for [Subneon.co](https://subneon.co/).

For task description see here: [TASK_DESCRIPTION.md](https://github.com/shadowsmind/company-filter/blob/master/docs/TASK_DESCRIPTION.md)

### How use

In `.src/main/resources/` replace existed `companies.csv` and `filters.json` with your real data.

Execute
> sbt run

Result will be saved to `top3_companies.csv` in project directory

### How work:

- read and parse filters from json file
- read and parse companies from csv file
- filter companies
- fold to top 3 companies by revenue
- save result to csv file

### Stack
- scala, sbt
- cats-effects
- fs2
- fs2-data
- circe
- scalatest

### TODO

1. More unit-tests

2. Better abstraction for `fold` on stream for create any samples:
   - TopByRevenue(size)
   - WorstByRevenue(size)
   - etc.

3. Use arguments for config paths to data, filters and result

### Remarks

1. Json format for filters inconvenient for parsing with auto-derive codecs:

    > { "filterType": "NameContains", "text": "foo" }
    
    better if we move specific filter fields to optional object:
    
    > { "filterType": "NameContains", "options": { "text": "foo"}  }
   
    in this variant we can create filter with polymorph options:
    
    > case class Filter[T](filterType: String, options: T)
    
    > case class ByNameFilterOption(text: String)
   
    and after with Circe (or other json-lib) easy derive codecs for our filters without using json cursors or other custom logic.


2. We can separate filter name and filter logic for better abstraction and composite logic:
    
    > { "filterType": "NameContains", "text": "foo" }
    > { "filterType": "IsProfitable" }
    
    with additional field:
    > { "name": "NameContains", "type": "ByField", "condition": "Contains", "options": { "value": "foo" } }
    > { "name": "IsProfitable", "type": "ByField", "condition": "Equals", "options": { "value": true } }