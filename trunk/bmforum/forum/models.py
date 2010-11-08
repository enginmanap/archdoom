from django.db import models
from datetime import datetime
from django.contrib.auth.models import User

class Entry(models.Model):
	user = models.ForeignKey(User, related_name = "entry_author")
	topic = models.ForeignKey(Topic, related_name = "topic_of_entry")
	text = models.TextField(verbose_name = "icerik")
	date = models.DateTimeField("date_submitted", default = datetime.now())
	isHidden = models.BooleanField("entry_deleted?", default = false)
	isVotable = models.BooleanField("entry_votable?", default = true)


#FIXME edit should be a new class	
	isEdited = models.BooleanField("entry_edited?", default = false)
	editdate = models.DateTimeField("date_edited")
	editBy = models.ForeignKey(User, related_name ="entry_editer")

class TopicPriorities(models.Model):
	name = models.CharField(max_length = 100)
	
	def __unicode__(self):
		return self.name

class Topic(models.Model):
	title = models.charField(max_length = 100, verbose_name = "baslik")
	priority = models.ForeignKey(TopicPriorities, blank=false, null=false, verbose_name = "onem")
	subTopic = models.ForeignKey(Topic,blank=true,null=true,verbose_name = "ait_oldugu_baslik")
	owner = models.ForeignKey(User, related_name = "creater_of_topic")
	



	
