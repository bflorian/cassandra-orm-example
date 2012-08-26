import grails.util.Environment
import cassandra.orm.example.User
import cassandra.orm.example.Gender
import cassandra.orm.example.Post
import cassandra.orm.example.Comment
import cassandra.orm.example.View
import cassandra.orm.example.ReferrerType

class BootStrap {

	def init = { servletContext ->
		runCqlScript("localhost", "test/data/schema-drop.txt")
		runCqlScript("localhost", "test/data/schema-create.txt")

		if (Environment.current == Environment.TEST) {
			bootstrapIntegrationTestData()
		}
	}

	def destroy = {
	}

	private runCqlScript(host, script) {
		def dsePath = System.getProperty('dsePath')
		def cassandraCli = "$dsePath/bin/cassandra-cli -h ${host} -f"

		def cmd = "$cassandraCli $script"
		log.debug "Running cassandra script with command: '$cmd'"
		def p = cmd.execute()
		def stderr = p.err?.text
		def stdout = p.text
		if (stderr) {
			log.error stderr
		} else {
			log.debug stdout
		}
	}

	private bootstrapIntegrationTestData()
	{
		def u1 = new User(
				username:  "matt",
				emailAddress: "matt@everywhere.com",
				firstName: "Matt",
				lastName: "Marvelous",
				address: "1 Main St",
				city: "Ellicott City",
				state: "MD",
				country: "USA",
				zip: "21043",
				phone: "+14105551212",
				gender: Gender.MALE,
				birthDate: Date.parse("yyyy-MM-dd","1992-01-05")
		).save()

		def u2 = new User(
				username:  "katie",
				emailAddress: "katie@everywhere.com",
				firstName: "Katie",
				lastName: "Courageous",
				address: "100 Avenue Del Sol",
				city: "Malaga",
				state: "Andalusia",
				country: "ESP",
				zip: "200034",
				phone: "+34923444121",
				gender: Gender.FEMALE,
				birthDate: Date.parse("yyyy-MM-dd","1992-09-01")
		).save()

		def u3 = new User(
				username:  "vicky",
				emailAddress: "vicky@everywhere.com",
				firstName: "Vicky",
				lastName: "Victorious",
				address: "1 Avenue Del Sol",
				city: "Olney",
				state: "MD",
				country: "USA",
				zip: "20832",
				phone: "+14105551212",
				gender: Gender.FEMALE,
				birthDate: Date.parse("yyyy-MM-dd","1995-12-25")
		).save()


		def p1 = new Post(
				title: "My very first post",
				text: "Four score and seven years ago",
				tags:  ["gettysburg"],
				occurTime: Date.parse("yyyy-MM-dd","2012-07-01")
		)
		u1.addToPosts(p1)

		def p2 = new Post(
				title: "My second post",
				text: "our fathers brought forth on this continent,",
				tags:  ["gettysburg","fathers"],
				occurTime: Date.parse("yyyy-MM-dd","2012-07-04")
		)
		u1.addToPosts(p2)

		def p3 = new Post(
				title: "Just getting started",
				text: "a new nation, conceived in Liberty,",
				tags:  ["gettysburg","liberty"],
				occurTime: Date.parse("yyyy-MM-dd","2012-08-05")
		)
		u1.addToPosts(p3)

		def p4 = new Post(
				title: "Another first post",
				text: "and dedicated to the proposition that all men are created equal.",
				tags:  ["gettysburg","liberty","equality"],
				occurTime: Date.parse("yyyy-MM-dd","2013-03-15")
		)
		u2.addToPosts(p4)


		def c = new Comment(
				text: "You tell um Abe!",
				occurTime: Date.parse("yyyy-MM-dd","2012-07-03")
		)
		p1.addToComments(c)

		c = new Comment(
				text: "Oh yeah!",
				occurTime: Date.parse("yyyy-MM-dd","2012-07-05")
		)
		p2.addToComments(c)

		c = new Comment(
				text: "Oh yeah, oh yeahhh!",
				occurTime: Date.parse("yyyy-MM-dd","2012-08-01")
		)
		p2.addToComments(c)

		c = new Comment(
				text: "You can say that again",
				occurTime: Date.parse("yyyy-MM-dd","2012-09-03")
		)
		p3.addToComments(c)

		c = new Comment(
				text: "Amen brother !!!",
				occurTime: Date.parse("yyyy-MM-dd","2013-06-03")
		)
		p4.addToComments(c)

		p1.addToViews(new View(occurTime: Date.parse("yyyy-MM-dd HH:mm","2013-06-03 08:10"), referrerType:  ReferrerType.DIRECT, ipAddress: "127.0.0.1"))
		p1.addToViews(new View(occurTime: Date.parse("yyyy-MM-dd HH:mm","2013-06-03 11:20"), referrerType:  ReferrerType.SEARCH, referrerName: "Google", ipAddress: "127.0.0.1"))
		p1.addToViews(new View(occurTime: Date.parse("yyyy-MM-dd HH:mm","2013-06-04 09:10"), referrerType:  ReferrerType.DIRECTORY, referrerName: "Citysearch", ipAddress: "127.0.0.1"))
		p2.addToViews(new View(occurTime: Date.parse("yyyy-MM-dd HH:mm","2013-06-05 07:10"), referrerType:  ReferrerType.SOCIAL, referrerName: "Facebook", ipAddress: "127.0.0.1"))
		p2.addToViews(new View(occurTime: Date.parse("yyyy-MM-dd HH:mm","2013-06-05 22:10"), referrerType:  ReferrerType.DIRECT, ipAddress: "127.0.0.1"))
		p2.addToViews(new View(occurTime: Date.parse("yyyy-MM-dd HH:mm","2013-06-06 10:10"), referrerType:  ReferrerType.SEARCH, referrerName: "Google", ipAddress: "127.0.0.1"))
		p3.addToViews(new View(occurTime: Date.parse("yyyy-MM-dd HH:mm","2013-07-01 08:10"), referrerType:  ReferrerType.DIRECTORY, referrerName: "Insiderpages", ipAddress: "127.0.0.1"))
		p3.addToViews(new View(occurTime: Date.parse("yyyy-MM-dd HH:mm","2013-07-03 08:10"), referrerType:  ReferrerType.SOCIAL, referrerName: "Twitter", ipAddress: "127.0.0.1"))
		p4.addToViews(new View(occurTime: Date.parse("yyyy-MM-dd HH:mm","2013-06-06 10:10"), referrerType:  ReferrerType.SEARCH, referrerName: "Bing", ipAddress: "127.0.0.1"))
		p4.addToViews(new View(occurTime: Date.parse("yyyy-MM-dd HH:mm","2013-07-01 08:10"), referrerType:  ReferrerType.DIRECTORY, referrerName: "Yahoo!", ipAddress: "127.0.0.1"))

	}
}
