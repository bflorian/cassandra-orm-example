package cassandra.orm.example

/**
 * @author: Bob Florian
 */
class Post 
{
	UUID uuid
	String title
	String text
	Date occurTime
	List tags

	User user
	static belongsTo = [user: User]

	List comments
	List views
	static hasMany = [comments: Comment, views: View]

	static cassandraMapping = [
			primaryKey: 'uuid',
			explicitIndexes: ["tags"],
			counters: [
					[groupBy: ['occurTime']],
					[groupBy: ['occurTime','tags']],
					[findBy:  ['user'], groupBy:['occurTime']]
			]
	]
}
