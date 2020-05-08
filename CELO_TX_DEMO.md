## Celo TX Web3j Setup

### Part 1: Celo TX Demo Setup
1. clone the [Celo Web3j Fork Repo](https://github.com/celo-org/web3j) `git clone https://github.com/celo-org/web3j`
2. checkout the demo branch `cd web3j && git checkout amyslawson/celo-tx`
3. build the demo by running `./gradlew build` in the root directory of this repo.

    Next, we'll set up the CLI

### Part 2: Celo TX DemoWeb3j-cli Setup
1. clone the [Web3j-cli Repo](https://github.com/celo-org/web3j-cli) `git clone https://github.com/celo-org/web3j-cli`
2. `cd web3j-cli && git checkout amyslawson/celo-tx`
3. change the build.gradle to point to your local web3j snapshots by replacing the dependency paths with the path to your web3j project https://github.com/web3j/web3j-cli/compare/master...celo-org:amyslawson/celo-tx#diff-c197962302397baf3a4cc36463dce5ea
4. run `./gradlew build` in the root directory of this repo to run compile and tests. Some tests are expected to fail but it should still compile.
5. Try it out!

    **Get Block Information**:
    
    run `./gradlew run --args='block'`
    
    The output should include the fee currency, gateway recipient, and gateway fee for a hardcoded block

    **Send a cUSD Transaction**:
    
    run `./gradlew run --args='transfer 0xd0a57D8acFe9979d33933d8A52971E6DC9E2DbF0 2'`
    
    This signs and transfers a transaction from account 0x0ed3510bf7adcce8c48eddf05df353407c578da5 with a hardcoded gasCost and gasLimit.  It prints out the senders balance and the receivers balance before and after transaction. You can check if this succeded by looking up transfer history for this address on (Celo Blockscout).[https://alfajores-blockscout.celo-testnet.org/address/0x0ed3510bf7adcce8c48eddf05df353407c578da5/transactions]

    **Get Identifiers for Phone Number**:

    run `./gradlew run --args='lookupIdentifer +15555555555' --stacktrace`

    This will retrieve the addresses associated with the phoneNumber and will output the first address.