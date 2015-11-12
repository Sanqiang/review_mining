require("FKF")
require("MASS")
id = "B001JKTTVQ"
path = paste("C:/git/review_mining/r/",id,"_new.txt",sep="")

rating = read.table(path,sep="\t",col.names=c("rating","time","text"),quote = "")
rating = rating[order(rating[,2]),]
rating = rating[1:dim(rating)[1],]
#write.table(rating,"B000CNB4LE_sorted.txt",row.names = FALSE,col.names = FALSE,quote = FALSE,sep = "\t")



rating = rating[,1]
rating = matrix(rating,20)

aggre <- function(row){
  c(length(which(row == 1)),length(which(row == 2)),length(which(row == 3)),length(which(row == 4)),length(which(row == 5)))/length(row)
}

## <--------------------------------------------------------------------------->
## Example 2: Local level model for the Nile's annual flow.
## <--------------------------------------------------------------------------->
## Transition equation:
## alpha[t+1] = alpha[t] + eta[t], eta[t] ~ N(0, HHt)
## Measurement equation:
## y[t] = alpha[t] + eps[t], eps[t] ~ N(0, GGt)
rating <- apply(rating,2,aggre)
residual = matrix(-1,nrow=dim(rating)[1],ncol=dim(rating)[2])
for(r_id in 1:5){
  y = rating[r_id,]
  ## Set constant parameters:
  dt <- ct <- matrix(0)
  Zt <- Tt <- matrix(1)
  a0 <- y[1] # Estimation of the first year flow
  P0 <- matrix(100) # Variance of 'a0'
  ## Estimate parameters:
  fit.fkf <- optim(c(HHt = var(y, na.rm = TRUE) * .5,
                     GGt = var(y, na.rm = TRUE) * .5),
                   fn = function(par, ...)
                     -fkf(HHt = matrix(par[1]), GGt = matrix(par[2]), ...)$logLik,
                   yt = rbind(y), a0 = a0, P0 = P0, dt = dt, ct = ct,
                   Zt = Zt, Tt = Tt, check.input = FALSE)
  ## Filter Nile data with estimated parameters:
  fkf.obj <- fkf(a0, P0, dt, ct, Tt, Zt, HHt = matrix(fit.fkf$par[1]),
                 GGt = matrix(fit.fkf$par[2]), yt = rbind(y))
  ## Compare with the stats' structural time series implementation:
  fit.stats <- StructTS(y, type = "level")
  fit.fkf$par
  fit.stats$coef
  ## Plot the flow data together with fitted local levels:
  plot(y,type='l')
  #lines(fitted(fit.stats), col = "green")
  lines(ts(fkf.obj$att[1, ], start = start(y), frequency = frequency(y)), col = "blue")
  
  residual[r_id,] = abs(fkf.obj$att[1, ] - y)
  #legend("top", c("Nile flow data", "Local level (StructTS)", "Local level (fkf)"),col = c("black", "green", "blue"), lty = 1)
  #break
}
write.matrix(residual,file=paste("C:/git/review_mining/r/weight_",id,".txt",sep=""),sep=" ")
sort(unique(c(as.matrix(residual))))

