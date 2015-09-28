# -*- coding: utf-8
from py2neo import Graph, Node, Relationship
from py2neo import neo4j, cypher, node,  rel
#from py2neo.legacy import Index
import pandas
graph = Graph("http://neo4j:123456@0.0.0.0:7474/db/data/")
RELATIONSHIP = "BELONGS_TO"
NODES = {
	"COUNTRY":"COUNTRY",
	"CITY":"CITY",
	"DISTRICT":"DISTRICT",
	"WARD":"WARD",
	"STREET":"STREET",
	"COMPANY":"COMPANY"
}
df = pandas.read_csv('new.csv',header=False)
num = 0
config={"type":"fulltext", "provider":"lucene"}
cities = {}
countries = {}
streets = {}
districts = {}
wards = {}
config = {}
batch = neo4j.WriteBatch(graph)
#belongs = batch.graph.legacy.get_or_create_index(neo4j.Relationship, "BELONGS_TO")
#country = batch.graph.legacy.get_or_create_index(neo4j.Node, "COUNTRY",config)
#city = batch.graph.legacy.get_or_create_index(neo4j.Node, "CITY",config)
#district = batch.graph.legacy.get_or_create_index(neo4j.Node, "DISTRICT",config)
#street = batch.graph.legacy.get_or_create_index(neo4j.Node, "STREET",config)
#ward = batch.graph.legacy.get_or_create_index(neo4j.Node, "WARD",config)
#company = batch.graph.legacy.get_or_create_index(neo4j.Node, "COMPANY",config)
for row in df.iterrows():
	#import ipdb; ipdb.set_trace()
	item = row[1]

	data_countries_json = eval(item['99'])
	

	company = batch.graph.legacy.get_or_create_index(neo4j.Node, "COMPANY",config)
	country_name = data_countries_json['country'].lower()
	city_name = data_countries_json['administrative_area_level_1'].lower()
	district_name = data_countries_json['administrative_area_level_2'] if data_countries_json['administrative_area_level_1'].lower() != '' else data_countries_json['locality'].lower()
	ward_name = data_countries_json['sublocality_level_1'].lower()
	street_name = data_countries_json['route'].lower()
	company_name = item['3']
	company_address = item['9']
	#city_node = graph.create({"name": city_name})
	#country_node = Node("COUNTRY", name=country_name)
	country_node = batch.graph.legacy.get_or_create_indexed_node("COUNTRY","name", country_name, {
		"name":country_name
	})
	batch.add_labels(country_node,"COUNTRY")
	node = country_node
	
	#import ipdb;ipdb.set_trace()

	
	if(city_name!=''):
		city_node = batch.graph.legacy.get_or_create_indexed_node("CITY","name", city_name, {
		"name":city_name
		})
		batch.add_labels(city_node,"CITY")
		if(not cities.has_key(city_node)):
			cities[city_node] = True
			batch.create(Relationship(node, "BELONGS_TO", city_node))
		node = city_node
	

	if(district_name!=''):
		district_node = batch.graph.legacy.get_or_create_indexed_node("DISTRICT","name", district_name, {
		"name":district_name
		})
		batch.add_labels(district_node,"DISTRICT")
		if(not districts.has_key(district_node)):
			districts[district_node] = True
			batch.create(Relationship(node, "BELONGS_TO", district_node))
		node = district_node
	


	if(ward_name!=''):
		ward_node = batch.graph.legacy.get_or_create_indexed_node("WARD","name", district_name, {
		"name":ward_name
		})
		batch.add_labels(ward_node,"WARD")
		if(not wards.has_key(ward_node)):
			wards[ward_node] = True
			batch.create(Relationship(node, "BELONGS_TO", ward_node))
		node = ward_node
	
	if(street_name!=''):
		street_node = batch.graph.legacy.get_or_create_indexed_node("STREET","name", street_name, {
		"name":street_name
		})
		batch.add_labels(street_node,"STREET")
		if(not streets.has_key(street_node)):
			streets[street_node] = True
			batch.create(Relationship(node, "BELONGS_TO", street_node))
		node = street_node

	company_node = batch.graph.legacy.get_or_create_indexed_node("COMPANY","name", company_name, {
		"name":company_name,
		"address":str(company_address)
		
	})
	batch.add_labels(company_node,"COMPANY")
	batch.create(Relationship(node, "BELONGS_TO", company_node))


	#belongs.get_or_create(
    #	"num", num, (country_node, "BELONGS_TO", city_node)
	#)
batch.submit()



