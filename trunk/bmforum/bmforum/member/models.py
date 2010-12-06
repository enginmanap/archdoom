from django.db import models
from django.contrib.auth.models import User
from bmforum.forum.models import Topic
from datetime import datetime

class NotifyOptions(models.Model):
    notifyPM = models.BooleanField(verbose_name="mesajda_uyar?")
    notifyTopic = models.ManyToManyField(Topic, related_name = "takip_ettigi_baslik")

class Member(models.Model):
    user = models.ForeignKey(User, unique=True)
    birthDate = models.DateTimeField("dogum_tarihi")
    studentNo = models.IntegerField(verbose_name = "ogrenci_numarasi")
    studentClass = models.IntegerField(verbose_name = "ogrenci_sinifi")
    notifyOption = models.ForeignKey(NotifyOptions, related_name = "uyarma_ayarlari")

class PrivateMessage(models.Model):
    title = models.CharField(max_length = 100, verbose_name="mesaj_basligi")
    text = models.TextField(verbose_name = "mesaj_icerigi")
    date = models.DateTimeField(verbose_name = "mesaj_yollama_tarihi", default=datetime.now())
    pmFrom = models.ForeignKey(User, related_name="mesaj_yollayan")
    pmTo = models.ForeignKey(User, related_name="mesaj_alan")
