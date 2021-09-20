<template>
  <div>
    <h3>test</h3>
    <div>
      <img :src="captchaImg" alt=""/>
      <hr>
      <el-image :src="captchaImg"></el-image>
      <hr>
      <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHgAAAAwCAIAAABVDSmEAAAOxUlEQVR42u1aCVBV1xnWaIzRpDFpM8nYNJsznU6atJlETczEJq3NYoyNMXFJY6ZqZRMQFDSiqERc2FRAZBHZRDYBQUBARBDFsAnILgKyg09Algc%2BeCyn33nnepf37gPbJs608/45w9x77uGc/3z3P////f99k4hBHopMMkBgANoAtEEMQBuANgBtEAPQBqANYgDaALQB6AeVe8Ok6DZJriO5rWRk7CfTYHiYlJWRe/f0DuhXk7w2klJHKjvJ8Oh/vtDgICkpIXV1RKEgAwM/J6hjY8rOhpaqdOXdpomBBpKNvSStnnheI2uTyGv%2B5BEnMukg12YfJfltE%2ByqtpZuDH9Hx0Xn%2BnVibEzMzcnJk%2BT2be2n7gVkhpuw7nRXMi%2BYrDtHnHJIej0ZUP8b2z99mi7EN2tr4uFBiot/Alj7OusbS5OKU50yAr%2BL3fdWgMUMP%2BNJaAGWM28VxWoDDWO51k5hxTbmBpGZou3JtreCZBaFscTFkf37iampsCU7O1JRoVfPsDBhpIkJ8fcnXV3cI5ybJw%2BNp8PjruTbBFLVOTEaPT1k40YJ0HwbRzdZGRkeVNzKrbjkffmUSbzTgsBNTzJYZZu/6VSGNQf01gxqLFrbgAm/7E0%2BjiQWacSjgB7eum7y97PCgPIOiQawXAsLYQPYGA83EMzNldd7zx7Oovl/tLeHldBHpXeEtaq76Or2WdSip7nQnmc9yC/d6cWjziRsIrDi44X5zcyIo6Nw6%2BYm6I8j2NHBrS4rDSUJJzY%2BpgsorDj6hzfO%2B3yZE2NbmeV7KXgd/yjI6qkRtYoD%2BjGN6k8cIp9Ekp1ZJLKSlN0hqmH6qFtFLjcJmLrmCps/rnPunJyo6g4OpKGB6h0dLexn%2B3Y5/9tP3wGeVlWRq1fpGDb45k36NKSMWwjeA3tfFiuxgz8GkPO3yLshnE3kjevKcKowbXAw9U5wa7yqrLW00J6aGu7W0pI4O5OICJKTo%2B3N8uN3UuysZ13wW5EXZ3cjO6DtZtZAd6vE4EbUsHfxa%2BhoKuKA/r0/VXdZrDD6dj85kk8WnORc8xQnUthO%2BxEM%2Ba0ap2jvJyaGA5pJW5vkkHZ0aI8vLaX9MPyhIXrr68uNjIqit7YZEjclDhKsveBFWvrIS970%2Bi/helGur%2BemjUivMEsyWxq%2B1Cpul4npGK/YlSt0GJyerG9pE71C%2BAEAF2b3kuxCt%2Bt%2BTDm65IT5dC17b644zwG9/hzV9TlP0qYk4RXk82gy1Vl7V9Ua1wkj4nve1nHTOHrMUfBhHX6A11g38iQk0H4cZLEb4V/Vp1HcQnDE1M3JuenMRpJUy5nCXZU80ImJ7HWOLQlZHloSWttVuzcgTxdK5k9gy%2BHhNE7a2nJPExKEqVR9d/xMJgO7gd52kIqydPc0n%2BUgGOxp8OZnZD11Y0kiB3RAyQSh77PTGi4xwtk%2Bay8e0%2BsKYKpMoDS/nxSdE%2BDlRftPneKICu/TQQkgzFTR9mbrBfpiA%2BWd7Dq3VR5oYIc512%2BrSqxOZD0I1%2BJYDent5TQHC2KCU8UGHDokmQ28AthF7JzDEEcDvkMD3dRuXP%2BkizKipbKrkQO6VSkTDMWNkbltmZJOvB5dgTFCudj7XqiwUNgSA1QszCnDO0PAcPmRu3dT6jb5/kJRVRKgE2qoo8AFQgtCyNUWrv9crYw%2BajWNfphz6R7/ATU9aEolhylroD0Q6MC8s/o%2BZTx8mBuwZYtkwuKUg7poll44TKnqeRdxZ9j2F/PObGceXKB3gXJGzba6Io4OyG6WeMmFp4hscAaa7ADyhI/fkqcnpdUIdDB8lqqwDbOTm50tjISzRnLEr3VdIQEaJ4ldgIRAvojhbi81yuiD5dicK733XLx1Uevd8w6NhQcfn/scboSCzo9hIYTjwYP98c7vaQ"/>
      <hr>
      <img src="../assets/img/Logo.png" />
    </div>
<!--  <GoodItemInOrder></GoodItemInOrder>-->
<!--    <table cellspacing="0" cellpadding="0" border="0" style="border-collapse: separate; border-spacing: 0px 50px">-->
<!--      <tr style="border: 1px solid">-->
<!--        <td style="border: 1px solid">123</td>-->
<!--      </tr>-->
<!--      <tr >-->
<!--        <td>b321</td>-->
<!--      </tr>-->
<!--    </table>-->
  </div>
</template>

<script>

export default {
  components: {
  },
  name: "test",
  data() {
    return {
      captchaImg: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHgAAAAwCAIAAABVDSmEAAAQqUlEQVR42u1aCVAUWZru6dnd6Z7Zid7emXFjIjp2endmdzp2diZmu0BAoAUEFW/aoxVRsVu5sVHxYJQGpfFqL0YFoSgUGyiRS1BOoQQBkaPkvm85i7uAAoqi6P2KB4%2BkKqvUbmJj3KiMFxkv/8x8lfm9P7//%2B/9Xb32n3f5Ptrfe3EeXSgYL75%2BMO6cf4fEh9nnR7mJRvRboRd7GR3qBbw7fpaelcLivWdSYV/TAO/TQLyseX9cCvZhbdphDfuxxJePoQFvYsQ/aKlK0QC/aBneWiLtU7Q2Fd9O527VAL9oWaMf%2B5KARzIEW6EXbQMegaVU7eCPK%2B7%2B1QC/aluS3urnkvqr90c0tpWkXtUAv2gaUo33%2BLJfLmEZIDlWjFugfuiHoxV8w6ntRjD72Ap4NSONvVkq/wUBjI9oZgRGOjP7U5Pibl7C0iQayi%2BsTskqTcsszhbXlDR3DkkV7DdGoKL89v3Wo9YcMMiGbqOmtyX2RWy4ql0/Lf8hQkyLRcH7%2BROsrPc%2BYTFYhEuW%2BeNE0ODgll39/oKPShTq7fN/i2Km2jzZ/dSsh9/skcrLxoo4i3nOeW7KbxR0LTiAHzSDY4NWxnv5uumuk61nbs7vld70fe2%2B9t1U3SJeMg7b629WVPZWv/jzy8fHhoqIuHq/eza3EwqKIw0F7bmAw/jKsI8rLDXk8TmAgaQbBwbtiY09lZt4uLs5vbx%2BXyV4J6Onp6X0%2Bd1ghpu3v9Rx7B0cKKpvTnlVp9nHJpATu5vfMzzbOVo%2BrR0Fhtmv519TdPiWfquqtAqynMk/tit1lxDNiHYG2nTE7XwKuRDKUm9vu51dtayvU0yPgKrX2a9c0jTA9bRwSQlFWbct4vJMZGc2Dgy8B%2BvCVKArof1p5NnX0Ssalsil5R89gTklDSHzuTwycmKC/u8zZ%2BkTw46JazBBzHGGn0CnRSZ%2Brr4SFZZilp8AzoTahc6TTOsYaFvsH9qyvhOmBvyvdDhdeH7HeOdH5fM55fjkfs9gubj%2BRcYJe0DjQyJ7FCIV1Tk5CfX0lWEstLZs9PfsSEqSdnVXW1rDU2ttrALq%2Bv59i2jo01C4W%2BxcWwqP1uVxYzENDzUJD0dHjcpPr69UCPTgs%2BbGuAwVx50me6i9xbNgp5cP1Ht6BDxraenBNx3AHEyOTWybuqe6RFZHNg8042z3SHV8TD3SIh1rdtWJ9JTKCcYixS6LLjYIbqQ2pDf0N0impAjXp8POu5xTTO6V36G/FVseyUHlHh9DAgIJbbGLS4O7eExk53qx4Hml3d198fNOJE8%2BNjHC2wspKA9APa2sJymAPeNbh1FSmO%2B%2BIjs5ra7ONi0NfNygIPM4ONIIeRQ3709yHqr/0%2BalQCu7buvbga1DNPxq7UuOfd/g4RB4hr43vvUJUgTA1Ih0RNAvOZp8FrEpO6vjQkfWVQME4ezj1MLX0j/WHl4Xvvb%2BXUDP21b3VsMOv6Wi%2BT3xVh2o9d45AXL1r12hFxXdy%2BdTIyKBA0Hr2bLmVlZKP1zk6agD6al4ewXRnTAwOgaYSdViGhYlGR9eGhaH/eWwcO9Df3EklYC3ffxH72w%2Beqv5SQHQWxdThTBgxjkgmePE5xvu%2BIfY/Xp5lDIcHDjgL/13KXaqOWDOaMlhfCbyMswibvZLelPoUhFDVQUggzWvLoxabGBvVocosLQmItQ6K54H/CpcuZSVotIGMDA1AuyYlEUBPzlzGStPGh678aqsHOjqBgeKJCRagqbeucLyMfWpeJY2Q6ANN9Gtbu8k1v914QjUSCgprcOpj/1kxsIG/AcYz2WdU8dUJ1NkTtyepPkndK2F6NIe%2BA8kHFB/%2BlJT4Pmlrw9eqDkV5o3yD4nlaz5xhgVhHp3rPnv6kJM3hdF14OEGTKxSqA/rnG9zfXmpP%2BuUM9p'
    }
  }
}
</script>

<style scoped>


</style>