from django.db import models
from datetime import datetime
from django.contrib.auth.models import User
from bmforum.member.models import Member

class TopicPriorities(models.Model):
    name = models.CharField(max_length = 100)
    priority = models.IntegerField()

    def __unicode__(self):
        return self.name

class Topic(models.Model):
	title = models.CharField(max_length = 100, verbose_name = "baslik")
	priority = models.ForeignKey(TopicPriorities, blank=False, null=False, verbose_name = "onem")
	subTopic = models.ForeignKey('self', blank=True, null=True, verbose_name = "ait_oldugu_baslik")
	owner = models.ForeignKey(Member, related_name = "creator_of_topic")
	firstEntry = models.ForeignKey('Entry', related_name = "ilk_girdi", null=True, blank=True)
	isHidden = models.BooleanField("topic_deleted?", default = False)
	date = models.DateTimeField("date_created", default = datetime.now())
	def __unicode__(self):
		return self.title

class Entry(models.Model):
	member = models.ForeignKey(Member, related_name = "entry_author")
	topic = models.ForeignKey(Topic, related_name = "topic_of_entry", null=False, blank=False)
	text = models.TextField(verbose_name = "icerik")
	date = models.DateTimeField("date_submitted", default = datetime.now())
	isHidden = models.BooleanField("entry_deleted?", default = False)
	isVotable = models.BooleanField("entry_votable?", default = True)
#FIXME edit should be a new class	
	isEdited = models.BooleanField("entry_edited?", default = False)
	editDate = models.DateTimeField("date_edited", blank=True, null=True)
	editBy = models.ForeignKey(Member, related_name ="entry_editer", blank=True, null=True)

	def __unicode__(self):
		return unicode(self.member.user.username) +" : "+ self.text[0:30]

class Vote(models.Model):
	vote = models.IntegerField()
	entry = models.ForeignKey("Entry")
	voter = models.ForeignKey(Member, related_name = "voter_user")
	
	def __unicode__(self):
		return unicode(self.voter)
