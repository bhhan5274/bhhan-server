package io.github.bhhan.portfolio.oauth2.web;

import io.github.bhhan.portfolio.common.error.ErrorCode;
import io.github.bhhan.portfolio.common.error.ErrorResponse;
import io.github.bhhan.portfolio.oauth2.exception.AccountException;
import io.github.bhhan.portfolio.oauth2.service.AccountService;
import io.github.bhhan.portfolio.oauth2.web.api.AccountDto;
import io.github.bhhan.portfolio.oauth2.web.api.ClientDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
public class AccountApi {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAccount(@Valid @RequestBody AccountDto.AccountReq accountReq){
        accountService.addAccount(accountReq);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
    }

    @PostMapping("/{accountId}/clientDetails")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto.AccountClientDetailsRes addClientDetails(@PathVariable Long accountId, @Valid @RequestBody ClientDetailsDto clientDetailsDto){
        return accountService.addClientDetails(accountId, clientDetailsDto);
    }

    @DeleteMapping("/{accountId}/clientDetails/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClientDetails(@PathVariable Long accountId, @PathVariable String clientId){
        accountService.deleteClientDetails(accountId, clientId);
    }

    @ExceptionHandler(value = AccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccountException(AccountException ex){
        return new ErrorResponse(ErrorCode.BAD_REQUEST, ex.getMessage());
    }
}
