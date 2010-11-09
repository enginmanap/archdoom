from django.db import models
from django.contrib.auth.models import User
from bmforum.forum.models import Topic

class NotifyOptions(models.Model):
    notifyPM = models.BooleanField(verbose_name="mesajda_uyar?")
    notifyTopic = models.ManyToManyField(Topic, related_name = "takip_ettigi_baslik")

class Member(models.Model):
    user = models.ForeignKey(User, unique=True)
    birthDate = models.DateTimeField("dogum_tarihi")
    studentNo = models.IntegerField(verbose_name = "ogrenci_numarasi")
    studentClass = models.IntegerField(verbose_name = "ogrenci_sinifi")
    notifyOption = models.ForeignKey(NotifyOptions, related_name = "uyarma_ayarlari")
