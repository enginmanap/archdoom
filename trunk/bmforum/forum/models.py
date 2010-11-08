from django.db import models
from datetime import datetime

# Create your models here.

class User(models.Model):




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

class Topic(models.Model):





	
