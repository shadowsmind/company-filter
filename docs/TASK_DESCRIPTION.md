Intro
Create an application that will:
read data about some companies from a file
filter them according to filter descriptions from another file
get top 3 company by descending their revenue (among filtered companies)

Source code should be placed in an open repository (e.g. github.com), no zip archives. Prepare this task as you do with features for your production (abstractions, code style, etc).

Focus on business logic, not on parsing command-line arguments. If input params can be set only via modifying several first lines of the source code of your app - it will be fine.
Step 1
Read a csv file from the local disk. Keep in mind this file can potentially contain lots of rows. Example -

companies.scv

CompanyName,Revenue,IsProfitable
"Company 1",100,true
"Company foo 1",101,true
"Company foo 2",102,false
"Company foo 3",103,true
"Company foo 4",104,true
"Company foo 5",105,true
etc
Step 2
Filter descriptions will be in the file -
filters.json
[
{
"filterType": "NameContains",
"text": "foo"
},
{
"filterType": "IsProfitable"
}
]

You need to support these two filters but make your solution extensible for new filters.
NameContains - should filter companies that have specific “text” in their CompanyName.
IsProfitable - keep companies with IsProfitable=true

Data from step 1 will be filtered to
"Company foo 1",101,true
"Company foo 3",103,true
"Company foo 4",104,true
"Company foo 5",105,true
Step 3
Among filtered companies get top by descending their revenue.

Top 3 from step 2:
"Company foo 5",105,true
"Company foo 4",104,true
"Company foo 3",103,true
